package com.airbnb.android.react.maps;

import android.content.Context;
import android.util.Log;

import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.TileOverlay;
import com.huawei.hms.maps.model.TileOverlayOptions;
//import com.google.maps.android.heatmaps.HeatmapTileProvider;
//import com.google.maps.android.heatmaps.WeightedLatLng;
//import com.google.maps.android.heatmaps.Gradient;

import java.util.Arrays;
import java.util.List;

import me.tatiyanupanwong.supasin.android.libraries.huawei.maps.utils.heatmaps.Gradient;
import me.tatiyanupanwong.supasin.android.libraries.huawei.maps.utils.heatmaps.HeatmapTileProvider;
import me.tatiyanupanwong.supasin.android.libraries.huawei.maps.utils.heatmaps.WeightedLatLng;

public class AirMapHeatmap extends AirMapFeature {

    private TileOverlayOptions heatmapOptions;
    private TileOverlay heatmap;
    private HeatmapTileProvider heatmapTileProvider;

    private List<WeightedLatLng> points;
    private Gradient gradient;
    private Double opacity;
    private Integer radius;

    public AirMapHeatmap(Context context) {
        super(context);
    }

    public void setPoints(WeightedLatLng[] points) {
        this.points = Arrays.asList(points);
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setWeightedData(this.points);
        }
        if (heatmap != null) {
            heatmap.clearTileCache();
        }
    }

    public void setGradient(Gradient gradient) {
        this.gradient = gradient;
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setGradient(gradient);
        }
        if (heatmap != null) {
            heatmap.clearTileCache();
        }
    }
    
    public void setOpacity(double opacity) {
        this.opacity = new Double(opacity);
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setOpacity(opacity);
        }
        if (heatmap != null) {
            heatmap.clearTileCache();
        }
    }

    public void setRadius(int radius) {
        this.radius = new Integer(radius);
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setRadius(radius);
        }
        if (heatmap != null) {
            heatmap.clearTileCache();
        }
    }

    public TileOverlayOptions getHeatmapOptions() {
        if (heatmapOptions == null) {
            heatmapOptions = createHeatmapOptions();
        }
        return heatmapOptions;
    }

    private TileOverlayOptions createHeatmapOptions() {
        TileOverlayOptions options = new TileOverlayOptions();
        if (heatmapTileProvider == null) {
            HeatmapTileProvider.Builder builder =
                new HeatmapTileProvider.Builder().weightedData(this.points);
            if (radius != null) {
                builder.radius(radius);
            }
            if (opacity != null) {
                builder.opacity(opacity);
            }
            if (gradient != null) {
                builder.gradient(gradient);
            }
            heatmapTileProvider = builder.build();
        }
        options.tileProvider(heatmapTileProvider);
        return options;
    }

    @Override
    public Object getFeature() {
        return heatmap;
    }

    @Override
    public void addToMap(HuaweiMap map) {
        Log.d("AirMapHeatmap", "ADD TO MAP");
        heatmap = map.addTileOverlay(getHeatmapOptions());
    }

    @Override
    public void removeFromMap(HuaweiMap map) {
        heatmap.remove();
    }

}