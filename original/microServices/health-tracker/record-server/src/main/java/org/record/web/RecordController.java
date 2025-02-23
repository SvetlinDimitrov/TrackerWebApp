package org.record.web;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.domain.record.paths.RecordControllerPaths;
import org.record.features.record.dto.RecordCreateRequest;
import org.record.features.record.dto.RecordUpdateReqeust;
import org.record.features.record.dto.RecordView;
import org.record.features.record.services.RecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RecordControllerPaths.BASE)
@RequiredArgsConstructor
public class RecordController {

  private final RecordService recordService;

  @GetMapping(RecordControllerPaths.GET_ALL)
  public ResponseEntity<Page<RecordView>> getAll(
      @RequestHeader(name = "X-ViewUser") String userToken, Pageable pageable) {
    return new ResponseEntity<>(recordService.getAll(userToken, pageable), HttpStatus.OK);
  }

  @GetMapping(RecordControllerPaths.GET_BY_ID)
  public ResponseEntity<RecordView> getById(
      @RequestHeader(name = "X-ViewUser") String userToken, @PathVariable UUID id) {
    return new ResponseEntity<>(recordService.getById(id, userToken), HttpStatus.OK);
  }

  @PostMapping(RecordControllerPaths.CREATE)
  public ResponseEntity<RecordView> create(@RequestHeader(name = "X-ViewUser") String userToken,
      @RequestBody @Valid RecordCreateRequest dto) {

    return ResponseEntity.ok(recordService.create(dto, userToken));
  }

  @PatchMapping(RecordControllerPaths.UPDATE)
  public ResponseEntity<RecordView> update(
      @RequestHeader(name = "X-ViewUser") String userToken, @PathVariable UUID id,
      @RequestBody @Valid RecordUpdateReqeust dto) {
    return ResponseEntity.ok(recordService.update(id, userToken ,dto));
  }

  @DeleteMapping(RecordControllerPaths.DELETE)
  public ResponseEntity<HttpStatus> delete(@RequestHeader(name = "X-ViewUser") String userToken,
      @PathVariable UUID id) {
    recordService.delete(id, userToken);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}