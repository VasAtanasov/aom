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
}
