package com.mantisa.inventory.controller;

import com.mantisa.inventory.model.ProductEntity;
import com.mantisa.inventory.model.UnitEntity;
import com.mantisa.inventory.model.dto.CreateProductDTO;
import com.mantisa.inventory.model.dto.ProductReportDTO;
import com.mantisa.inventory.repository.UnitRepository;
import com.mantisa.inventory.service.implementation.ProductServiceImpl;
import com.mantisa.inventory.util.ResponseObject;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/products")
public class ProductController {
    private final Logger log = org.slf4j.LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private UnitRepository unitRepository;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> create(@RequestBody CreateProductDTO createProductDTO) {
        try {
            // Validar tipo de producto
            if (createProductDTO.getProductType() != 1 && createProductDTO.getProductType() != 2) {
                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Invalid product type", null);
            }

            boolean isCreation = createProductDTO.getProductId() == 0; // Determinar si es creación
            String code;

            if (isCreation) {
                // Generar nuevo ID y código para el nuevo producto
                Long newProductId = productService.findLastProductId() + 1;
                String prefix = createProductDTO.getProductType() == 1 ? "PT" : "MP";
                code = prefix + "-" + newProductId;
            } else {
                // Obtener producto existente para actualizar
                ProductEntity existingProduct = productService.getById((long) createProductDTO.getProductId());
                if (existingProduct == null) {
                    return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Product not found", null);
                }
                code = existingProduct.getCode(); // Mantener el código existente
            }

            // Verificar existencia de la unidad
            Optional<UnitEntity> unitEntityOptional = unitRepository.findById((long) createProductDTO.getFkUnitId());
            if (unitEntityOptional.isEmpty()) {
                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Unit not found", null);
            }

            // Crear o actualizar el producto
            ProductEntity productEntity = ProductEntity.builder()
                    .productId(isCreation ? null : (long) createProductDTO.getProductId())
                    .code(code)
                    .name(createProductDTO.getName())
                    .description(createProductDTO.getDescription())
                    .productType(createProductDTO.getProductType())
                    .status(createProductDTO.isStatus())
                    .stock(createProductDTO.getStock())
                    .unit(unitEntityOptional.get())
                    .build();

            ProductEntity productCreated = productService.save(productEntity);

            // Devolver respuesta adecuada
            if (!isCreation) {
                return ResponseObject.build(true, HttpStatus.OK, "Product updated successfully", productCreated);
            }
            return ResponseObject.build(true, HttpStatus.CREATED, "Product created successfully", productCreated);
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

    @PostMapping("/get-all")
    public ResponseEntity<ResponseObject> getAll() {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Products retrieved successfully", productService.getAll());
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseObject> delete(@Param("id") Long id) {
        try {
            // Check if the product has production orders
            if (productService.productHasProductionOrders(id)) {
                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "The product has production orders", null);
            }

            productService.delete(id);
            return ResponseObject.build(true, HttpStatus.OK, "Product deleted successfully", null);
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

    @PostMapping("/get-by-code")
    public ResponseEntity<ResponseObject> getByCode(@Param("code") String code) {
        try {
            ProductEntity product = productService.getByCode(code);
            if (product == null) {
                return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Product not found", null);
            }
            return ResponseObject.build(true, HttpStatus.OK, "Product retrieved successfully", productService.getByCode(code));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

    @PostMapping("/get-by-id")
    public ResponseEntity<ResponseObject> getById(@Param("id") Long id) {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Product retrieved successfully", productService.getById(id));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

    @PostMapping("/get-all-units")
    public ResponseEntity<ResponseObject> getAllUnits() {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Units retrieved successfully", productService.getAllUnits());
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

    @PostMapping("/get-all-by-type")
    public ResponseEntity<ResponseObject> getAllByType(@Param("type") int type) {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Products retrieved successfully", productService.getAllByProductType(type));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurró un error", e.getMessage());
        }
    }

    @PostMapping("/get-inventory-report")
    public ResponseEntity<byte[]> getInventoryReport(@Param("type") int type) {
        try {
            if (type != 1 && type != 2) {
                return ResponseEntity.badRequest().body(null);
            }

            List<ProductEntity> products = productService.getAllByProductType(type);
            List<ProductReportDTO> productReportDTOS = products.stream().map(product -> ProductReportDTO.builder()
                    .code(product.getCode())
                    .name(product.getName())
                    .description(product.getDescription())
                    .productType(product.getProductType() == 1 ? "Producto terminado" : "Materia prima")
                    .unitName(product.getUnit().getName())
                    .stock(product.getStock())
                    .status(product.isStatus() ? "Activo" : "Inactivo")
                    .build()).toList();
            // Ruta del archivo .jrxml
            InputStream reportStream = getClass().getResourceAsStream("/reports/inventory_report.jrxml");
            // Compilar el archivo .jrxml
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Llenar el reporte con los datos
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productReportDTOS);
            Map<String, Object> parameters = new HashMap<>();

            // Llenar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Exportar el reporte a PDF
            byte[] pdfReport = JasperExportManager.exportReportToPdf(jasperPrint);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "inventory_report.pdf");

            return new ResponseEntity<>(pdfReport, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
