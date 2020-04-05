import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;

public class CollisionResistance {

    public static int breakHash(byte[] sliceOfDigest, MessageDigest md) {
        int noOfAttempts = 0;
        for (int l = 0; l < 256; l++) {
            for (int i = 0; i < 256; i++) {
                for (int j = 0; j < 256; j++) {
                    for (int k = 0; k < 256; k++) {
                        noOfAttempts++;
                        byte[] genMsg = {(byte) l, (byte) i, (byte) j, (byte) k};
                        md.update(genMsg);
                        byte[] msgDigest = md.digest();
                        byte[] sliceOfGenDigest = getSliceOfArray(msgDigest, 0, 3);
                        if (genMsg[0] == sliceOfGenDigest[0] && sliceOfGenDigest[1] == sliceOfDigest[1] && sliceOfGenDigest[2] == sliceOfDigest[2]) {
                            return noOfAttempts;
                        }
                    }
                }
            }
        }
        return -1;
    }

    public static byte[] getSliceOfArray(byte[] arr, int startIndex, int endIndex) {
        return Arrays.copyOfRange(arr, startIndex, endIndex);
    }

    public static void main(String[] args) {
        String message = args[0];
        String digestAlgorithm = "SHA-256";
        String textEncoding = "UTF-8";

        try {
            MessageDigest md = MessageDigest.getInstance(digestAlgorithm);
            byte[] inputBytes = message.getBytes(textEncoding);
            md.update(inputBytes);
            byte[] digest = md.digest();
            byte[] sliceOfDigest = getSliceOfArray(digest, 0, 3);
            int noOfAttempts = breakHash(sliceOfDigest, md);
            System.out.println(noOfAttempts);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
