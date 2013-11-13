package org.energyos.espi.common.domain;

public class Configuration {
    public static final String DATA_CUSTODIAN_ID = "data_custodian";
    public static final String[] SCOPES = new String [] {
            "FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13",
            "FB=4_5_16;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13"
    };

    public static final String HTTP_WWW_W3_ORG_2005_ATOM = "http://www.w3.org/2005/Atom";
    public static final String HTTP_NAESB_ORG_ESPI = "http://naesb.org/espi";
    public static final String ATOM_PREFIX = "";
    public static final String ESPI_PREFIX = "espi";
}
