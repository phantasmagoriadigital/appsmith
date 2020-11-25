package com.appsmith.external.models;

import com.appsmith.external.annotations.DocumentType;
import com.appsmith.external.constants.AuthType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@DocumentType(AuthType.DB_AUTH)
public class DBAuth extends AuthenticationDTO {
    public enum Type {
        SCRAM_SHA_1, SCRAM_SHA_256, MONGODB_CR, USERNAME_PASSWORD
    }

    Type authType;

    String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    String databaseName;

    @Override
    public Map<String, String> getEncryptionFields() {
        if(this.password != null) {
            return Map.of("password", this.password);
        }
        return Map.of();
    }

    @Override
    public void setEncryptionFields(Map<String, String> encryptedFields) {
        if(encryptedFields != null && encryptedFields.containsKey("password")) {
            this.password = encryptedFields.get("password");
        }
    }

    @Override
    public Set<String> getEmptyEncryptionFields() {
        if(this.password == null || this.password.isEmpty())
            return Set.of("password", null);
        return Set.of();
    }

}
