package br.edu.fbuni.openstack.perftest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@RestController
@RequestMapping(value = "/api/test")
public class PerftestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerftestApplication.class, args);
    }

    @RequestMapping(value = "/memory/{size}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getMultipleMemoryAllocationOperations(@PathVariable Integer size) {
        for (int i = 0; i < 100; i++) {
            int[] a = new int[size];
        }
        return ResponseEntity.ok(size);
    }

    @RequestMapping(value = "/file/{size}", method = RequestMethod.GET)
    public ResponseEntity<Integer> getMultipleFileOperations(@PathVariable Integer size) throws IOException {
        Path path = Paths.get("file.txt");
        String s = IntStream.range(0, size).mapToObj(String::valueOf).collect(Collectors.joining(System.lineSeparator()));
        byte[] bytes = s.getBytes(Charset.defaultCharset());

        for (int i = 0; i < 100; i++) {
            Files.write(path, bytes);
        }
        Files.delete(path);

        return ResponseEntity.ok(size);
    }

}
