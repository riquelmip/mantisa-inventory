package com.mantisa.inventory.controller;

import com.mantisa.inventory.model.*;
import com.mantisa.inventory.model.dto.AssignOrdenDTO;
import com.mantisa.inventory.model.dto.CreateOrdenDTO;
import com.mantisa.inventory.model.dto.CreateProductDTO;
import com.mantisa.inventory.model.dto.OrderDetailDTO;
import com.mantisa.inventory.repository.ProductionOrderDetailRepository;
import com.mantisa.inventory.repository.UnitRepository;
import com.mantisa.inventory.service.implementation.ProductServiceImpl;
import com.mantisa.inventory.service.implementation.ProductionLineServiceImpl;
import com.mantisa.inventory.service.implementation.ProductionOrderServiceImpl;
import com.mantisa.inventory.util.ResponseObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/production-orders")
public class ProductionOrderController {
    @Autowired
    private ProductionOrderServiceImpl productionOrderService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ProductionOrderDetailRepository productionOrderDetailRepository;
    @Autowired
    private ProductionLineServiceImpl productionLineService;

//    @PostMapping("/create")
//    public ResponseEntity<ResponseObject> create(@RequestBody CreateOrdenDTO createOrdenDTO) {
//        try {
//            int lastOrderNumber = productionOrderService.findLastOrderNumber();
//            int newOrderNumber = lastOrderNumber + 1;
//
//            if (createOrdenDTO.getCustomer() == null || createOrdenDTO.getCustomer().isEmpty())
//                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Customer is required", null);
//
//            if (createOrdenDTO.getFkRequestedProductId() == 0)
//                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Requested product is required", null);
//
//            if (createOrdenDTO.getQuantity() == 0)
//                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Quantity is required", null);
//
//            if (createOrdenDTO.getDeliveryDate() == null)
//                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Delivery date is required", null);
//
//            if (createOrdenDTO.getOrderDetails().isEmpty())
//                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Order details are required", null);
//
//            ProductEntity requestedProduct = productService.getById((long) createOrdenDTO.getFkRequestedProductId());
//            if (requestedProduct == null)
//                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Requested product not found", null);
//
//            ProductionOrderEntity productionOrderEntity = ProductionOrderEntity.builder()
//                    .orderNumber(newOrderNumber)
//                    .customerName(createOrdenDTO.getCustomer())
//                    .requestedProduct(requestedProduct)
//                    .quantity(createOrdenDTO.getQuantity())
//                    .deliveryDate(createOrdenDTO.getDeliveryDate())
//                    .status(0) // 0. "pendiente"
//                    .build();
//
//            ProductionOrderEntity productionOrderCreated = productionOrderService.save(productionOrderEntity);
//
//            List<OrderDetailDTO> orderDetails = createOrdenDTO.getOrderDetails();
//            List<ProductionOrderDetailEntity> productionOrderDetails = List.of();
//            for (OrderDetailDTO orderDetail : orderDetails) {
//                ProductEntity product = productService.getById((long) orderDetail.getProductId());
//                if (product == null)
//                    return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Product not found", null);
//
//                // Validate if product is in stock
//                if (product.getStock() < orderDetail.getQuantity())
//                    return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Product " + product.getName() + " out of stock", null);
//
//                productionOrderDetails.add(ProductionOrderDetailEntity.builder()
//                        .product(product)
//                        .quantity(orderDetail.getQuantity())
//                        .productionOrder(productionOrderCreated)
//                        .build());
//            }
//
//            // Save order details
//            productionOrderDetailRepository.saveAll(productionOrderDetails);
//
//            // Update stock
//            for (ProductionOrderDetailEntity productionOrderDetail : productionOrderDetails) {
//                ProductEntity product = productionOrderDetail.getProduct();
//                product.setStock(product.getStock() - productionOrderDetail.getQuantity());
//                productService.save(product);
//            }
//
//            return ResponseObject.build(true, HttpStatus.OK, "Production order created successfully", productionOrderCreated);
//        } catch (Exception e) {
//            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
//        }
//    }


    @PostMapping("/create")
    @Transactional // Asegura que todas las operaciones se realicen como una transacción
    public ResponseEntity<ResponseObject> create(@RequestBody CreateOrdenDTO createOrdenDTO) {
        try {
            // Validaciones de entrada
            if (isInvalidCreateOrdenDTO(createOrdenDTO)) {
                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Invalid input data", null);
            }

            // Obtener el siguiente número de orden
            int newOrderNumber = productionOrderService.findLastOrderNumber() + 1; // Obtiene el último y suma uno

            // Obtener el producto solicitado
            ProductEntity requestedProduct = productService.getById((long) createOrdenDTO.getFkRequestedProductId());
            if (requestedProduct == null) {
                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Requested product not found", null);
            }

            // Crear la entidad de orden de producción
            ProductionOrderEntity productionOrderEntity = ProductionOrderEntity.builder()
                    .orderNumber(newOrderNumber) // Usa el nuevo número de orden como int
                    .customerName(createOrdenDTO.getCustomer())
                    .requestedProduct(requestedProduct)
                    .quantity(createOrdenDTO.getQuantity())
                    .deliveryDate(createOrdenDTO.getDeliveryDate())
                    .status(0) // 0: "pendiente"
                    .build();

            // Guardar la orden de producción
            ProductionOrderEntity productionOrderCreated = productionOrderService.save(productionOrderEntity);

            // Procesar los detalles de la orden
            List<ProductionOrderDetailEntity> productionOrderDetails = new ArrayList<>();
            for (OrderDetailDTO orderDetail : createOrdenDTO.getOrderDetails()) {
                ProductEntity product = productService.getById((long) orderDetail.getProductId());
                if (product == null) {
                    throw new Exception("Product not found: " + orderDetail.getProductId()); // Lanza una excepción si el producto no se encuentra
                }

                // Validar stock
                if (product.getStock() < orderDetail.getQuantity()) {
                    throw new Exception("Product " + product.getName() + " out of stock"); // Lanza una excepción si no hay suficiente stock
                }

                // Agregar detalle de orden a la lista
                productionOrderDetails.add(ProductionOrderDetailEntity.builder()
                        .product(product)
                        .quantity(orderDetail.getQuantity())
                        .productionOrder(productionOrderCreated)
                        .build());

                // Actualizar stock
                product.setStock(product.getStock() - orderDetail.getQuantity());
            }

            // Guardar todos los detalles de la orden
            productionOrderDetailRepository.saveAll(productionOrderDetails);

            // Actualizar stock para todos los productos (esto se hace fuera del bucle)
            productService.saveAll(productionOrderDetails.stream()
                    .map(ProductionOrderDetailEntity::getProduct)
                    .collect(Collectors.toList()));

            return ResponseObject.build(true, HttpStatus.OK, "Production order created successfully", productionOrderCreated);
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

    @PostMapping("/get-all")
    public ResponseEntity<ResponseObject> getAll() {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Products retrieved successfully", productionOrderService.getAll());
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

    private boolean isInvalidCreateOrdenDTO(CreateOrdenDTO createOrdenDTO) {
        // Validaciones
        if (createOrdenDTO.getCustomer() == null || createOrdenDTO.getCustomer().isEmpty()) {
            return true; // El cliente es requerido
        }

        if (createOrdenDTO.getFkRequestedProductId() == 0) {
            return true; // El producto solicitado es requerido
        }

        if (createOrdenDTO.getQuantity() == 0) {
            return true; // La cantidad es requerida
        }

        if (createOrdenDTO.getDeliveryDate() == null) {
            return true; // La fecha de entrega es requerida
        }

        if (createOrdenDTO.getOrderDetails() == null || createOrdenDTO.getOrderDetails().isEmpty()) {
            return true; // Los detalles de la orden son requeridos
        }

        return false; // Si pasa todas las validaciones, no es inválido
    }

    @PostMapping("/assign-to-production-line")
    public ResponseEntity<ResponseObject> assignToProductionLine(@RequestBody AssignOrdenDTO assignOrdenDTO) {
        try {
            ProductionOrderEntity productionOrder = productionOrderService.getById((long) assignOrdenDTO.getIdOrder());
            ProductionLineTypeEntity productionLineType = productionLineService.getProductionLineTypeById((long) assignOrdenDTO.getFkProductionLineTypeId());

            if (productionOrder == null) {
                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Production order not found", null);
            }

            if (productionLineType == null) {
                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Production line type not found", null);
            }

            if (productionOrder.getStatus() != 0) {
                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Production order is not pending", null);
            }

            productionOrder.setStatus(1); // 1: "en proceso"
            productionOrderService.save(productionOrder);

            // Crear la linea de producción
            ProductionLineEntity productionLine = ProductionLineEntity.builder()
                    .productionOrder(productionOrder)
                    .startDate(new Date())
                    .productionLineType(productionLineType)
                    .status(1) // 1: "en producción"
                    .build();

            return ResponseObject.build(true, HttpStatus.OK, "Production order assigned to production line", productionOrder);
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

    @PostMapping("/finish-production")
    public ResponseEntity<ResponseObject> finishProduction(@Param("id") Long productionLineId) {
        try {
            ProductionLineEntity productionLine = productionLineService.getById(productionLineId);

            if (productionLine == null) {
                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Production line order not found", null);
            }

            if (productionLine.getStatus() != 1) {
                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Production line order is not in process", null);
            }

            productionLine.setStatus(2); // 2: "terminada"
            productionLine.setEndDate(new Date());
            productionLineService.save(productionLine);

            ProductionOrderEntity productionOrder = productionLine.getProductionOrder();
            productionOrder.setStatus(2); // 2: "terminada"
            productionOrderService.save(productionOrder);

            ProductEntity requestedProduct = productionOrder.getRequestedProduct();
            // Actualizar stock
            requestedProduct.setStock(requestedProduct.getStock() + productionOrder.getQuantity());
            productService.save(requestedProduct);

            return ResponseObject.build(true, HttpStatus.OK, "Production order finished", productionOrder);
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

}
