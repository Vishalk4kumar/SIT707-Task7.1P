package sit707_week7;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BodyTemperatureMonitorTest {

    private BodyTemperatureMonitor bodyTemperatureMonitor;
    private TemperatureSensor temperatureSensor;
    private CloudService cloudService;
    private NotificationSender notificationSender;

    @Before
    public void setUp() {
        temperatureSensor = Mockito.mock(TemperatureSensor.class);
        cloudService = Mockito.mock(CloudService.class);
        notificationSender = Mockito.mock(NotificationSender.class);

        bodyTemperatureMonitor = new BodyTemperatureMonitor(temperatureSensor, cloudService, notificationSender);
    }

    @Test
    public void testReadTemperature() {
        Mockito.when(temperatureSensor.readTemperatureValue()).thenReturn(37.0);
        double temperature = bodyTemperatureMonitor.readTemperature();
        Assert.assertEquals(37.0, temperature, 0.0);
    }

    @Test
    public void testReportTemperatureReadingToCloud() {
        TemperatureReading temperatureReading = new TemperatureReading();
        temperatureReading.setBodyTemperature(37.0);
        bodyTemperatureMonitor.reportTemperatureReadingToCloud(temperatureReading);
        Mockito.verify(cloudService).sendTemperatureToCloud(temperatureReading);
    }

    @Test
    public void testInquireBodyStatusNormalNotification() {
        Mockito.when(cloudService.queryCustomerBodyStatus(Mockito.any())).thenReturn("NORMAL");
        bodyTemperatureMonitor.inquireBodyStatus();
        Mockito.verify(notificationSender).sendEmailNotification(Mockito.any(Customer.class), Mockito.eq("Thumbs Up!"));
    }

    @Test
    public void testInquireBodyStatusAbnormalNotification() {
        Mockito.when(cloudService.queryCustomerBodyStatus(Mockito.any())).thenReturn("ABNORMAL");
        bodyTemperatureMonitor.inquireBodyStatus();
        Mockito.verify(notificationSender).sendEmailNotification(Mockito.any(FamilyDoctor.class), Mockito.eq("Emergency!"));
    }

    @Test
    public void testStudentIdentity() {
        String studentId = "222342946";
        Assert.assertNotNull("Student ID is null", studentId);
    }

    @Test
    public void testStudentName() {
        String studentName = "Vishal Kumar";
        Assert.assertNotNull("Student name is null", studentName);
    }

    @Test
    public void testReadTemperatureNegative() {
        Mockito.when(temperatureSensor.readTemperatureValue()).thenReturn(-1.0); // Mocking a negative temperature
        double temperature = bodyTemperatureMonitor.readTemperature();
        Assert.assertTrue("Temperature should be negative", temperature < 0);
    }

    @Test
    public void testReadTemperatureZero() {
        Mockito.when(temperatureSensor.readTemperatureValue()).thenReturn(0.0);
        double temperature = bodyTemperatureMonitor.readTemperature();
        Assert.assertEquals(0.0, temperature, 0.0);
    }

    @Test
    public void testReadTemperatureNormal() {
        Mockito.when(temperatureSensor.readTemperatureValue()).thenReturn(36.5);
        double temperature = bodyTemperatureMonitor.readTemperature();
        Assert.assertEquals(36.5, temperature, 0.0);
    }

    @Test
    public void testReadTemperatureAbnormallyHigh() {
        Mockito.when(temperatureSensor.readTemperatureValue()).thenReturn(40.0);
        double temperature = bodyTemperatureMonitor.readTemperature();
        Assert.assertEquals(40.0, temperature, 0.0);
    }
}
