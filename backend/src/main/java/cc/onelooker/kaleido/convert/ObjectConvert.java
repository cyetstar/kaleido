package cc.onelooker.kaleido.convert;

/**
 * @Author cyetstar
 * @Date 2023-05-04 18:06:00
 * @Description TODO
 */
public interface ObjectConvert {

    ObjectConvert INSTANCE = new ObjectConvert() {
    };

    default Boolean convertToBoolean(Object o) {
        return o != null;
    }
}
