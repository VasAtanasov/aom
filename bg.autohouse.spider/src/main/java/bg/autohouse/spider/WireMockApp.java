package bg.autohouse.spider;

import com.github.tomakehurst.wiremock.client.WireMock;

import static com.github.tomakehurst.wiremock.client.WireMock.getRecordingStatus;

public class WireMockApp {
  public static void main(String[] args) {
    //        WireMock wireMock = new WireMock(18080);

    WireMock.configureFor(18080);
    var mappings = WireMock.listAllStubMappings().getMappings();
    int f = 5;
    //    stubFor(proxyAllTo(CGApiClientImpl.API_BASE_URL));
    //    WireMock.startRecording(
    //        recordSpec().captureHeader("Accept").captureHeader("Content-Type", true));

    System.out.println(getRecordingStatus().getStatus());
    int c = 5;
    //    List<StubMapping> recordedMappings = WireMock.snapshotRecord();
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
