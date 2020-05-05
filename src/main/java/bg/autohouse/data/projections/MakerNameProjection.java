package bg.autohouse.data.projections;

public interface MakerNameProjection {
  String getName();

  VehicleProjection getVehicle();

  interface VehicleProjection {

    String getMakerName();
  }
}
