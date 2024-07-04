package springcloudms.orderservice.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class InventoryClientStub {

    public static void stubClientCall(String skuCode, Integer quantity) {

        stubFor(get(urlEqualTo("/api/inventory/inner/exists?skuCode=" + skuCode + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
//                        .withBody("{\"skuCode\":\"" + skuCode + "\",\"quantity\":" + quantity + "}")));
                        .withBody("true")));
    }
}
