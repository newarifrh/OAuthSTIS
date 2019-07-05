package dev.blank.oauthstis;

class UtilsApi {

    private static String BASE_URL_WS_STIS = "https://ws.stis.ac.id/";

    static BaseApiService getAPIService() {
        return NetworkHandler.getClient(getBaseUrlApi()).create(BaseApiService.class);
    }


    private static String getBaseUrlApi() {
        return BASE_URL_WS_STIS;
    }


}
