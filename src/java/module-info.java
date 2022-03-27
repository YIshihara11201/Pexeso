module pexeso.pexeso {
  requires javafx.controls;
  requires javafx.fxml;

  opens com.pexeso to javafx.fxml;
  exports com.pexeso;
}