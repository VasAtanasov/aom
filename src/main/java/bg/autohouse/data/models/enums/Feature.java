package bg.autohouse.data.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Feature implements Textable {
  ABS("ABS"),
  DRIVER_SIDE_AIRBAG("Driver-side airbag"),
  PASSENGER_SIDE_AIRBAG("Passenger-side airbag"),
  SUNROOF("Sunroof"),
  RADIO("Radio"),
  POWER_WINDOWS("Power windows"),
  ALLOY_WHEELS("Alloy wheels"),
  CENTRAL_DOOR_LOCK("Central door lock"),
  ALARM_SYSTEM("Alarm system"),
  NAVIGATION_SYSTEM("Navigation system"),
  IMMOBILIZER("Immobilizer"),
  SIDE_AIRBAG("Side airbag"),
  SEAT_HEATING("Seat heating"),
  HANDICAPPED_ENABLED("Handicapped enabled"),
  CRUISE_CONTROL("Cruise control"),
  XENON_HEADLIGHTS("Xenon headlights"),
  ON_BOARD_COMPUTER("On-board computer"),
  ELECTRONIC_STABILITY_CONTROL("Electronic stability control"),
  FOG_LIGHTS("Fog lights"),
  TRAILER_HITCH("Trailer hitch"),
  AIR_CONDITIONING("Air conditioning"),
  ROOF_RACK("Roof rack"),
  POWER_STEERING("Power steering"),
  AUTOMATIC_CLIMATE_CONTROL("Automatic climate control"),
  TRACTION_CONTROL("Traction control"),
  ELECTRICALLY_ADJUSTABLE_SEATS("Electrically adjustable seats"),
  MP3("MP3"),
  PANORAMA_ROOF("Panorama roof"),
  AUXILIARY_HEATING("Auxiliary heating"),
  SPORT_PACKAGE("Sport package"),
  START_STOP_SYSTEM("Start-stop system"),
  MULTI_FUNCTION_STEERING_WHEEL("Multi-function steering wheel"),
  DAYTIME_RUNNING_LIGHTS("Daytime running lights"),
  SPORT_SUSPENSION("Sport suspension"),
  SPORT_SEATS("Sport seats"),
  ADAPTIVE_HEADLIGHTS("Adaptive headlights"),
  SKI_BAG("Ski bag"),
  ADAPTIVE_CRUISE_CONTROL("Adaptive Cruise Control"),
  ARMREST("Armrest"),
  ELECTRICALLY_HEATED_WINDSHIELD("Electrically heated windshield"),
  HEATED_STEERING_WHEEL("Heated steering wheel"),
  HILL_HOLDER("Hill Holder"),
  DIGITAL_RADIO("Digital radio"),
  ELECTRIC_TAILGATE("Electric tailgate"),
  LED_HEADLIGHTS("LED Headlights"),
  LED_DAYTIME_RUNNING_LIGHTS("LED Daytime Running Lights"),
  LEATHER_STEERING_WHEEL("Leather steering wheel"),
  LUMBAR_SUPPORT("Lumbar support"),
  AIR_SUSPENSION("Air suspension"),
  MASSAGE_SEATS("Massage seats"),
  DRIVER_DROWSINESS_DETECTION("Driver drowsiness detection"),
  NIGHT_VIEW_ASSIST("Night view assist"),
  EMERGENCY_BRAKE_ASSISTANT("Emergency brake assistant"),
  EMERGENCY_SYSTEM("Emergency system"),
  TIRE_PRESSURE_MONITORING_SYSTEM("Tire pressure monitoring system"),
  SHIFT_PADDLES("Shift paddles"),
  SLIDING_DOOR("Sliding door"),
  KEYLESS_CENTRAL_DOOR_LOCK("Keyless central door lock"),
  SEAT_VENTILATION("Seat ventilation"),
  SOUND_SYSTEM("Sound system"),
  VOICE_CONTROL("Voice Control"),
  LANE_DEPARTURE_WARNING_SYSTEM("Lane departure warning system"),
  BLIND_SPOT_MONITOR("Blind spot monitor"),
  TOUCH_SCREEN("Touch screen"),
  TELEVISION("Television"),
  USB("USB"),
  TRAFFIC_SIGN_RECOGNITION("Traffic sign recognition"),
  ELECTRICAL_SIDE_MIRRORS("Electrical side mirrors"),
  BLUETOOTH("Bluetooth"),
  HEADS_UP_DISPLAY("Heads-up display"),
  HANDS_FREE_EQUIPMENT("Hands-free equipment"),
  ISOFIX("Isofix"),
  LIGHT_SENSOR("Light sensor"),
  RAIN_SENSOR("Rain sensor"),
  PARKING_ASSIST_SYSTEM_SENSORS_FRONT("Parking assist system sensors front"),
  PARKING_ASSIST_SYSTEM_SENSORS_REAR("Parking assist system sensors rear"),
  PARKING_ASSIST_SYSTEM_CAMERA("Parking assist system camera"),
  PARKING_ASSIST_SYSTEM_SELF_STEERING("Parking assist system self-steering"),
  CD_PLAYER("CD player"),
  GUARANTEE("Guarantee"),
  WITH_FULL_SERVICE_HISTORY("With full service history"),
  PARTICULATE_FILTER("Particulate filter"),
  NON_SMOKING_VEHICLE("Non-smoking vehicle"),
  METALLIC("Metallic");

  private final String text;

  @Override
  public String toString() {
    return text;
  }
}

// @ElementCollection(targetClass=EAuthority.class,fetch=FetchType.EAGER)
// 	@JoinTable(name = "grupo_autorities")
// 	@Enumerated(EnumType.STRING)
// 	public List<EAuthority> getAuthorities() {
// 		return authorities;
// 	}

// @ElementCollection
// @CollectionTable(name = "serveroptions_disallowuninstall", joinColumns = @JoinColumn(name =
// "id"), schema = "cloudconductor")
// @Column(name = "disallowuninstall")
// public Set<String> getDisallowUninstall() {
// 	return this.disallowUninstall;
// }

// @ElementCollection(fetch = FetchType.LAZY)
// @CollectionTable(name = "TASK_DATA_JOINED_BRANCHES", joinColumns = { @JoinColumn(name = "JOB_ID",
// referencedColumnName = "TASK_ID_JOB"),
//                                                                      @JoinColumn(name =
// "TASK_ID", referencedColumnName = "TASK_ID_TASK") }, indexes = { @Index(name =
// "TASK_DATA_JB_JOB_ID", columnList = "JOB_ID"),
//
//                                                        @Index(name = "TASK_DATA_JB_TASK_ID",
// columnList = "TASK_ID"), })
// @BatchSize(size = 100)
// public List<DBTaskId> getJoinedBranches() {
//     return joinedBranches;
// }

// @ElementCollection
// @CollectionTable(name = "storage_tags",
// 	foreignKey = @ForeignKey(name = RdbmsConstants.PREFIX + "storage_tags_fk_storage"),
// 	joinColumns = {
// 		@JoinColumn(name = "owner_id", referencedColumnName = "owner_id"),
// 		@JoinColumn(name = "storage_name", referencedColumnName = "name")
// })
// @MapKeyColumn(name = "tag", nullable = false)
// @Column(name = "value", nullable = false)
// public Map<String, String> getTags() {
// 	return tags;
// }

// @ElementCollection
// @CollectionTable(name = "bucket_object_version_tags",
// 	foreignKey = @ForeignKey(name = RdbmsConstants.PREFIX +
// "bucket_object_version_tags_fk_bucket_object_version"),
// 	joinColumns = {
// 		@JoinColumn(name = "bucket_id", referencedColumnName = "bucket_id"),
// 		@JoinColumn(name = "object_name", referencedColumnName = "object_name"),
// 		@JoinColumn(name = "version_uuid", referencedColumnName = "version_uuid")
// })
// @MapKeyColumn(name = "tag", nullable = false)
// @Column(name = "value", nullable = false)
// public Map<String, String> getTags() {
// 	return tags;
// }

// @ElementCollection
// @CollectionTable(name = "account_tags",
// 	foreignKey = @ForeignKey(name = RdbmsConstants.PREFIX + "account_tags_fk_user_account"),
// 	joinColumns = {
// 		@JoinColumn(name = "account_id", referencedColumnName = "id")
// })
// @MapKeyColumn(name = "tag", nullable = false)
// @Column(name = "value", nullable = false)
// public Map<String, String> getTags() {
// 	return tags;
// }

// @ElementCollection
// @CollectionTable(name="image_SubjectPart", joinColumns=@JoinColumn(name="image_id"))
// @Column(name="subjectPart")
// @Enumerated(value = EnumType.STRING)
// public Set<DescriptionType> getSubjectPart() {
// 	return subjectPart;
// }
