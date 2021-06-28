package cucumbermarket.cucumbermarketspring.domain.member.avatar.storage;

public class StorageExtensionException extends StorageException {
    public StorageExtensionException(String message) {
        super(message);
    }

    public StorageExtensionException(String message, Throwable cause) {
        super(message, cause);
    }
}
