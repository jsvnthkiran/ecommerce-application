package com.ecommerce.service;

import com.ecommerce.dao.DeliveryScheduleDAO;
import com.ecommerce.model.DeliverySchedule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Date; // Import java.sql.Date
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryScheduleServiceImplTest {

    private DeliveryScheduleServiceImpl deliveryScheduleService;
    private DeliveryScheduleDAOStub deliveryScheduleDAOStub;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() {
        deliveryScheduleDAOStub = new DeliveryScheduleDAOStub();
        deliveryScheduleService = new DeliveryScheduleServiceImpl(deliveryScheduleDAOStub);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    void testAddDeliverySchedule() throws SQLException, ParseException {
        // Convert String to java.sql.Date
        java.util.Date utilDate = dateFormat.parse("2024-08-25");
        java.sql.Date deliveryDate = new java.sql.Date(utilDate.getTime());

        DeliverySchedule deliverySchedule = new DeliverySchedule(1, 101, deliveryDate);

        deliveryScheduleService.addDeliverySchedule(deliverySchedule);

        assertEquals(1, deliveryScheduleDAOStub.deliverySchedules.size());
        assertEquals(deliverySchedule, deliveryScheduleDAOStub.deliverySchedules.get(0));
    }

    @Test
    void testGetDeliverySchedulesBySubscriptionId() throws SQLException, ParseException {
        // Convert Strings to java.sql.Date
        java.util.Date utilDate1 = dateFormat.parse("2024-08-25");
        java.sql.Date deliveryDate1 = new java.sql.Date(utilDate1.getTime());

        java.util.Date utilDate2 = dateFormat.parse("2024-08-26");
        java.sql.Date deliveryDate2 = new java.sql.Date(utilDate2.getTime());

        DeliverySchedule deliverySchedule1 = new DeliverySchedule(1, 101, deliveryDate1);
        DeliverySchedule deliverySchedule2 = new DeliverySchedule(2, 101, deliveryDate2);
        deliveryScheduleDAOStub.deliverySchedules.add(deliverySchedule1);
        deliveryScheduleDAOStub.deliverySchedules.add(deliverySchedule2);

        List<DeliverySchedule> schedules = deliveryScheduleService.getDeliverySchedulesBySubscriptionId(101);

        assertEquals(2, schedules.size());
        assertTrue(schedules.contains(deliverySchedule1));
        assertTrue(schedules.contains(deliverySchedule2));
    }

    @Test
    void testUpdateDeliverySchedule() throws SQLException, ParseException {
        // Convert String to java.sql.Date
        java.util.Date utilDate = dateFormat.parse("2024-08-25");
        java.sql.Date initialDate = new java.sql.Date(utilDate.getTime());

        DeliverySchedule deliverySchedule = new DeliverySchedule(1, 101, initialDate);
        deliveryScheduleDAOStub.deliverySchedules.add(deliverySchedule);

        // Convert String to java.sql.Date for updated schedule
        utilDate = dateFormat.parse("2024-08-26");
        java.sql.Date updatedDate = new java.sql.Date(utilDate.getTime());

        DeliverySchedule updatedSchedule = new DeliverySchedule(1, 101, updatedDate);
        deliveryScheduleService.updateDeliverySchedule(updatedSchedule);

        assertEquals(updatedDate, deliveryScheduleDAOStub.deliverySchedules.get(0).getDeliveryDate());
    }

    @Test
    void testDeleteDeliverySchedule() throws SQLException, ParseException {
        // Convert String to java.sql.Date
        java.util.Date utilDate = dateFormat.parse("2024-08-25");
        java.sql.Date deliveryDate = new java.sql.Date(utilDate.getTime());

        DeliverySchedule deliverySchedule = new DeliverySchedule(1, 101, deliveryDate);
        deliveryScheduleDAOStub.deliverySchedules.add(deliverySchedule);

        deliveryScheduleService.deleteDeliverySchedule(1);

        assertTrue(deliveryScheduleDAOStub.deliverySchedules.isEmpty());
    }

    // Stub class for DeliveryScheduleDAO
    static class DeliveryScheduleDAOStub implements DeliveryScheduleDAO {

        List<DeliverySchedule> deliverySchedules = new ArrayList<>();

        @Override
        public void addDeliverySchedule(DeliverySchedule deliverySchedule) throws SQLException {
            deliverySchedules.add(deliverySchedule);
        }

        @Override
        public List<DeliverySchedule> getDeliverySchedulesBySubscriptionId(int subscriptionId) throws SQLException {
            List<DeliverySchedule> result = new ArrayList<>();
            for (DeliverySchedule schedule : deliverySchedules) {
                if (schedule.getSubscriptionId() == subscriptionId) {
                    result.add(schedule);
                }
            }
            return result;
        }

        @Override
        public void updateDeliverySchedule(DeliverySchedule deliverySchedule) throws SQLException {
            for (int i = 0; i < deliverySchedules.size(); i++) {
                if (deliverySchedules.get(i).getId() == deliverySchedule.getId()) {
                    deliverySchedules.set(i, deliverySchedule);
                    break;
                }
            }
        }

        @Override
        public void deleteDeliverySchedule(int id) throws SQLException {
            deliverySchedules.removeIf(schedule -> schedule.getId() == id);
        }
    }
}
