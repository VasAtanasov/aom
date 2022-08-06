package bg.autohouse.spider;

import static com.github.tomakehurst.wiremock.client.WireMock.proxyAllTo;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import bg.autohouse.spider.api.HttpStrategy;
import bg.autohouse.spider.client.CGApiClientImpl;
import bg.autohouse.spider.client.JavaHttpClientStrategy;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import java.util.List;

public class WireMockApp {
  public static void main(String[] args) {

    HttpStrategy httpStrategy = new JavaHttpClientStrategy();
    CGApiClientImpl cg = new CGApiClientImpl(httpStrategy);
    //        WireMock wireMock = new WireMock(18080);

    WireMock.configureFor(18080);
    var mappings = WireMock.listAllStubMappings().getMappings();
    int c = 5;
    stubFor(proxyAllTo(cg.apiBaseUrl()).atPriority(1));
    List<StubMapping> recordedMappings = WireMock.snapshotRecord();
    //        var mappings = wireMock.allStubMappings().getMappings();
    int a = 5;

    //        wireMock.startStubRecording(cg.apiBaseUrl());
    //        var snapshotRecordResult = wireMock.stopStubRecording();
    //        int b = 5;

    //        wireMock.startStubRecording(WireMock.recordSpec()
    //                .forTarget(cg.apiBaseUrl())
    //                .onlyRequestsMatching(getRequestedFor(urlPathMatching("/Cars/.*"))));
  }
}
