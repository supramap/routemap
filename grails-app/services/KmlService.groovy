import java.text.SimpleDateFormat

class KmlService {

    boolean transactional = true

    public static String tempFolder(String oldFolder) {
        if (oldFolder != null)
            new AntBuilder().delete(dir: "/tmp/routemap/${oldFolder}")

        SimpleDateFormat sdf = new SimpleDateFormat('MMddHHmmss')
        def folder = sdf.format(new Date())
        new File("/tmp/routemap/${folder}").mkdirs()
        return folder
    }
}