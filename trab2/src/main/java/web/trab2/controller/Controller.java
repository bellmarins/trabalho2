package web.trab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.trab2.config.Utils;
import web.trab2.model.Aluno;
import web.trab2.model.AlunoDto;
import web.trab2.repository.AlunoRepository;

import java.util.ArrayList;

@RestController
public class Controller {

    @Autowired
    private AlunoRepository repository;

    @GetMapping("/getAll")
    public ResponseEntity<ArrayList<Aluno>> getAll() {
    	 ArrayList<Aluno> alunos = (ArrayList<Aluno>) repository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(alunos);
    }

    @PostMapping("/updateAluno")
    public ResponseEntity<Object> updateAluno(@RequestBody AlunoDto dto) {
    	Optional<Aluno> alunoOptional = repository.findById(dto.getId());
        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();
            aluno.setNome(dto.getNome());
            aluno.setIdade(dto.getIdade());
            aluno.setTurma(dto.getTurma());
            repository.save(aluno);
            return ResponseEntity.status(HttpStatus.OK).body(aluno);
        } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há aluno com este id");;
        }
    }

    @PostMapping("/novoAluno")
    public ResponseEntity<Object> novoAluno(@RequestBody AlunoDto dto) {
    	int totalAlunosNaTurma = repository.countByTurma(dto.getTurma());
        if (totalAlunosNaTurma >= 10) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Dados em excesso");
        }

        Aluno novoAluno = new Aluno();
        novoAluno.setNome(dto.getNome());
        novoAluno.setIdade(dto.getIdade());
        novoAluno.setTurma(dto.getTurma());
        repository.save(novoAluno);
        return ResponseEntity.status(HttpStatus.OK).body(novoAluno);
    }

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteAluno(@RequestBody String id) {
        /*
            Deixei este código presente por cortesia, assim como os dois métodos abaixo.
         */
        Long alunoId = Long.parseLong(id);
        this.repository.deleteById(alunoId);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/reset")
    public ResponseEntity<Object> reset() {
        this.repository.deleteAll();
        Utils.startDb(this.repository);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @GetMapping("/zerar")
    public ResponseEntity<Object> zerar() {
        this.repository.deleteAll();
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
