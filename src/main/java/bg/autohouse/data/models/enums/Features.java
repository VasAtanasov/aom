package bg.autohouse.data.models.enums;

/** Features */
public enum Features {}

// https://www.programcreek.com/java-api-examples/?api=javax.persistence.ElementCollection
// https://www.programcreek.com/java-api-examples/?code=ow2-proactive%2Fscheduling%2Fscheduling-master%2Fscheduler%2Fscheduler-server%2Fsrc%2Fmain%2Fjava%2Forg%2Fow2%2Fproactive%2Fscheduler%2Fcore%2Fdb%2FTaskData.java#
// https://www.programcreek.com/java-api-examples/?code=Blazebit%2Fblaze-storage%2Fblaze-storage-master%2Fcore%2Fmodel%2Fsrc%2Fmain%2Fjava%2Fcom%2Fblazebit%2Fstorage%2Fcore%2Fmodel%2Fjpa%2FStorage.java#
// https://www.programcreek.com/java-api-examples/?code=RBGKew%2Fpowop%2Fpowop-master%2Femonocot-model%2Fsrc%2Fmain%2Fjava%2Forg%2Femonocot%2Fmodel%2FDescription.java#

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
