package com.mantisa.inventory.service.implementation;

import com.mantisa.inventory.model.ProductEntity;
import com.mantisa.inventory.model.ProductionOrderEntity;
import com.mantisa.inventory.model.dto.OrdersReportDTO;
import com.mantisa.inventory.repository.ProductRepository;
import com.mantisa.inventory.repository.ProductionOrderRepository;
import com.mantisa.inventory.service.IProductService;
import com.mantisa.inventory.service.IProductionOrderService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductionOrderServiceImpl implements IProductionOrderService {
    private final Logger log = org.slf4j.LoggerFactory.getLogger(ProductionOrderServiceImpl.class);
    @Autowired
    private ProductionOrderRepository productionOrderRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public ProductionOrderEntity save(ProductionOrderEntity productionOrderEntity) {
        return productionOrderRepository.save(productionOrderEntity);
    }

    @Override
    public List<ProductionOrderEntity> getAll() {
        return productionOrderRepository.findAll();
    }

    @Override
    public ProductionOrderEntity getById(Long id) {
        return productionOrderRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        productionOrderRepository.deleteById(id);
    }

    @Override
    public int findLastOrderNumber() {
        return productionOrderRepository.findLastOrderNumber();
    }

    @Override
    public List<OrdersReportDTO> getOrdersReport(int status, String deliveryDate) {
        String sql = "SELECT po.order_number, po.customer_name, po.delivery_date, " +
                "pl.start_date, pl.end_date, po.status " +
                "FROM production_orders po " +
                "INNER JOIN production_lines pl ON pl.fk_production_order_id = po.production_order_id " +
                "WHERE po.status = ? AND DATE(po.delivery_date) = ?";

        return jdbcTemplate.query(sql, new Object[]{status, deliveryDate}, (rs, rowNum) -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            return OrdersReportDTO.builder()
                    .order_number(rs.getString("order_number"))
                    .customer_name(rs.getString("customer_name"))
                    .delivery_date(formatDate(rs.getTimestamp("delivery_date"), formatter))
                    .start_date(formatDate(rs.getTimestamp("start_date"), formatter))
                    .end_date(formatDate(rs.getTimestamp("end_date"), formatter))
                    .status(convertStatus(rs.getInt("status")))
                    .build();
        });
    }

    private String formatDate(Timestamp timestamp, DateTimeFormatter formatter) {
        if (timestamp != null) {
            return LocalDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault()).format(formatter);
        }
        return null; // O cualquier valor predeterminado que desees
    }


    private String convertStatus(Integer status) {
        switch (status) {
            case 0:
                return "Pendiente";
            case 1:
                return "En proceso";
            case 2:
                return "Finalizada";
            default:
                return "Desconocido";
        }
    }
}
