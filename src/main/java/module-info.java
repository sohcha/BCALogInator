module loginator.loginator {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires java.sql;
    exports loginator;
    requires org.apache.poi.ooxml;
    requires org.apache.poi.poi;
}
