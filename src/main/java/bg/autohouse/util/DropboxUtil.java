package bg.autohouse.util;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public final class DropboxUtil {
  public static final String RAW_TYPE_POSTFIX = "&raw=1";

  public static SharedLinkMetadata createSharedLinkFromPath(DbxClientV2 client, String path) {
    try {
      return client.sharing().createSharedLinkWithSettings(path);
    } catch (DbxException e) {
      log.error("Creating shared link failed.");
      e.printStackTrace();
    }
    return null;
  }

  public static SharedLinkMetadata getSharedLinkMetadata(DbxClientV2 client, String url) {
    try {
      return client.sharing().getSharedLinkMetadata(url);
    } catch (DbxException e) {
      log.error("Retrieving shared link failed.");
      e.printStackTrace();
    }
    return null;
  }

  //   public static List<String> dropboxGetFiles(String code) {

  //     DbxRequestConfig config = new DbxRequestConfig("Media Information Service Configuration");
  //     DbxClientV2 client = new DbxClientV2(config, code);
  //     ListFolderResult result = null;
  //     List<String> elements = new LinkedList<String>();

  //     try {
  //         result = client.files().listFolderBuilder("/media").withRecursive(true).start();
  //         while (true) {
  //             for (Metadata metadata : result.getEntries()) {
  //                 if (metadata instanceof FileMetadata) {
  //                     elements.add(metadata.getName());
  //                 }
  //             }

  //             if (!result.getHasMore()) {
  //                 break;
  //             }

  //             result = client.files().listFolderContinue(result.getCursor());
  //         }

  //         //System.out.println(elements.toString());
  //     } catch (DbxException e) {
  //         e.printStackTrace();
  //     }

  //     return elements;

  // }

  // public synchronized Metadata exists(String path) throws DbxException {
  //   // TODO validate path
  //   return client.files().getMetadata(path);
  // }

  //   public synchronized Metadata deleteFile(String path) throws DbxException {
  //     return client.files().delete(path);
  // }

  //   public List<String> listFiles() throws Exception {
  //     if (authSession()) {
  //         try {
  //             List<String> files = new ArrayList<String>();
  //             ListFolderResult listFolderResult = dropboxClient.files().listFolder("");
  //             for (Metadata metadata : listFolderResult.getEntries()) {
  //                 String name = metadata.getName();
  //                 if (name.endsWith(".backup")) {
  //                     files.add(name);
  //                 }
  //             }
  //             Collections.sort(files, new Comparator<String>() {
  //                 @Override
  //                 public int compare(String s1, String s2) {
  //                     return s2.compareTo(s1);
  //                 }
  //             });
  //             return files;
  //         } catch (Exception e) {
  //             Log.e("Financisto", "Dropbox: Something wrong", e);
  //             throw new ImportExportException(R.string.dropbox_error, e);
  //         }
  //     } else {
  //         throw new ImportExportException(R.string.dropbox_auth_error);
  //     }
  // }

  //   public static DropboxFileData convertMetadata(final Metadata metadata) {
  //     final DropboxFileData.DropboxFileDataBuilder builder = DropboxFileData.builder()
  //         .changeType(DropboxChangeType.fromMetadata(metadata))
  //         .pathDisplay(metadata.getPathDisplay())
  //         .pathLower(metadata.getPathLower());

  //     if (metadata instanceof FolderMetadata) {
  //         final FolderMetadata folderMetadata = (FolderMetadata) metadata;
  //         builder.id(folderMetadata.getId());

  //     } else if (metadata instanceof FileMetadata) {
  //         final FileMetadata fileMetadata = (FileMetadata) metadata;
  //         final ZoneId zoneId = ZoneOffset.UTC;
  //         final Instant clientModifiedInstant = fileMetadata.getClientModified().toInstant();
  //         final Instant serverModifiedInstant = fileMetadata.getServerModified().toInstant();
  //         builder
  //             .id(fileMetadata.getId())
  //             .rev(fileMetadata.getRev())
  //             .size(fileMetadata.getSize())
  //             .clientModified(LocalDateTime.ofInstant(clientModifiedInstant, zoneId))
  //             .serverModified(LocalDateTime.ofInstant(serverModifiedInstant, zoneId));
  //     }

  //     return builder.build();
  // }

  //   public Object syncUploadFile(DbxClientV2 mDbxClient, String localUri, String remoteFolder) {
  //     Log.d(TAG, "localUri: " + localUri);
  //     File localFile = new File(localUri);
  //     if (localFile.exists()) {
  //         // Note - this is not ensuring the name is a valid dropbox file name
  //         String remoteFileName = localFile.getName();
  //         try (InputStream inputStream = new FileInputStream(localFile)) {
  //             return mDbxClient.files().uploadBuilder(remoteFolder + "/" + remoteFileName)
  //                     .withMode(WriteMode.OVERWRITE).uploadAndFinish(inputStream);
  //         } catch (Exception e) {
  //             Log.e(TAG, "stringUploadFile", e);
  //             return e;
  //         }
  //     }
  //     return null;
  // }

  // private Map<String, Object> downloadFilesInFolder(String path) throws DropboxException {
  //   try {
  //     ListFolderResult folderResult = client.files().listFolder(path.equals("/") ? "" : path);
  //     Map<String, Object> returnMap = new LinkedHashMap<>();
  //     for (Metadata entry : folderResult.getEntries()) {
  //       returnMap.put(entry.getPathDisplay(),
  // downloadSingleFile(entry.getPathDisplay()).getValue());
  //     }
  //     return returnMap;
  //   } catch (ListFolderErrorException e) {
  //     try {
  //       DbxDownloader<FileMetadata> listing = client.files().download(path);
  //       if (listing == null) {
  //         return Collections.emptyMap();
  //       } else {
  //         LOG.debug("downloading a single file...");
  //         Map.Entry<String, Object> entry = downloadSingleFile(path);
  //         return Collections.singletonMap(entry.getKey(), entry.getValue());
  //       }
  //     } catch (DbxException dbxException) {
  //       throw new DropboxException(dbxException);
  //     }
  //   } catch (DbxException e) {
  //     throw new DropboxException(e);
  //   }
  // }

}
