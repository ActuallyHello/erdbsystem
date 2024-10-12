package com.ustu.erdb.base.api;

import com.ustu.erdb.base.services.impl.EnumerationService;
import com.ustu.erdb.base.services.impl.EnumerationValueService;
import com.ustu.erdb.base.store.models.Enumeration;
import com.ustu.erdb.base.store.models.EnumerationValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enumeration-values")
public class EnumerationValueController {

    @Autowired
    private EnumerationService enumerationService;

    @Autowired
    private EnumerationValueService enumerationValueService;

    @GetMapping("/{enumerationCode}")
    public ResponseEntity<List<EnumerationValue>> getByEnumerationCode(@PathVariable String enumerationCode) {
        Enumeration enumeration = enumerationService.getByCode(enumerationCode);
        List<EnumerationValue> byEnumeration = enumerationValueService.getByEnumeration(enumeration);
        return ResponseEntity.ok(byEnumeration);
    }
}
