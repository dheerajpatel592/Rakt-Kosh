package com.g4x.bloodapp;


public class BloodStock {
    private int  whole_blood , cryoprecipitate , packed_red_blood_cells
            , fresh_frozen_plasma , platelet_concentrate,irradiated_rbc ,
            platelet_poor_plasma , platelet_rich_plasma;

    public BloodStock() {
    }

    public BloodStock(int whole_blood, int cryoprecipitate, int red_blood_cells, int fresh_frozen_plasma, int concentrate, int irradiated_rbc, int platelet_poor_plasma, int platelet_rich_plasma) {
        this.whole_blood = whole_blood;
        this.cryoprecipitate = cryoprecipitate;
        this.packed_red_blood_cells = red_blood_cells;
        this.fresh_frozen_plasma = fresh_frozen_plasma;
        this.platelet_concentrate = concentrate;
        this.irradiated_rbc = irradiated_rbc;
        this.platelet_poor_plasma = platelet_poor_plasma;
        this.platelet_rich_plasma = platelet_rich_plasma;
    }

    public int getWhole_blood() {
        return whole_blood;
    }

    public void setWhole_blood(int whole_blood) {
        this.whole_blood = whole_blood;
    }

    public int getCryoprecipitate() {
        return cryoprecipitate;
    }

    public void setCryoprecipitate(int cryoprecipitate) {
        this.cryoprecipitate = cryoprecipitate;
    }

    public int getPacked_red_blood_cells() {
        return packed_red_blood_cells;
    }

    public void setPacked_red_blood_cells(int packed_red_blood_cells) {
        this.packed_red_blood_cells = packed_red_blood_cells;
    }

    public int getFresh_frozen_plasma() {
        return fresh_frozen_plasma;
    }

    public void setFresh_frozen_plasma(int fresh_frozen_plasma) {
        this.fresh_frozen_plasma = fresh_frozen_plasma;
    }

    public int getPlatelet_concentrate() {
        return platelet_concentrate;
    }

    public void setPlatelet_concentrate(int platelet_concentrate) {
        this.platelet_concentrate = platelet_concentrate;
    }

    public int getIrradiated_rbc() {
        return irradiated_rbc;
    }

    public void setIrradiated_rbc(int irradiated_rbc) {
        this.irradiated_rbc = irradiated_rbc;
    }

    public int getPlatelet_poor_plasma() {
        return platelet_poor_plasma;
    }

    public void setPlatelet_poor_plasma(int platelet_poor_plasma) {
        this.platelet_poor_plasma = platelet_poor_plasma;
    }

    public int getPlatelet_rich_plasma() {
        return platelet_rich_plasma;
    }

    public void setPlatelet_rich_plasma(int platelet_rich_plasma) {
        this.platelet_rich_plasma = platelet_rich_plasma;
    }
}
