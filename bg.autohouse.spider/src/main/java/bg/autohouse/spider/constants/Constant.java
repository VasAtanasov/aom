package bg.autohouse.spider.constants;

public final class Constant
{

    public static final String CG_CORE_ENDPOINT = "https://www.cargurus.com/Cars";


    public static final String CARS_TRIMS_URL =
            "https://www.cargurus.com/Cars/getSelectedModelCarTrimsAJAX.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT&model=";

    public static final String TRIM_TRANSMISSION_URL =
            "https://www.cargurus.com/Cars/getTransmissionListTrimFirstJson.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT&trim=";

    public static final String TRIM_ENGINE_URL =
            "https://www.cargurus.com/Cars/getEngineList.action?showInactive=true&useInventoryService=false&quotableCarsOnly=false&localCountryCarsOnly=false&outputFormat=REACT&trim=";

    public static final String TRIM_OPTIONS_FORM_URL =
            "https://www.cargurus.com/Cars/getOptionsJson.action";

    public static final int BATCH_SIZE = 50;

}
