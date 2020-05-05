package gamehub.api.dto;

import org.apache.commons.lang3.StringUtils;

public class ApiCurrentGameDTO {

    private final boolean processing;
    private final String guid;

    public ApiCurrentGameDTO(String guid) {
        if (StringUtils.isNotBlank(guid)) {
            this.processing = true;
            this.guid = guid;
        } else {
            this.processing = false;
            this.guid = null;
        }
    }

    public boolean isProcessing() {
        return processing;
    }

    public String getGuid() {
        return guid;
    }
}
