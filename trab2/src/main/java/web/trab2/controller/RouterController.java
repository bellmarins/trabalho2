package web.trab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import web.trab2.repository.AlunoRepository;

@Controller
public class RouterController {

	@Autowired
	AlunoRepository repository;

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/novo")
	public String novoAluno() {
		return "novo";
	}


	@GetMapping("/edit/{id}")
	public ModelAndView editNotas(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("edit");
Long alunoId = Long.parseLong(id);
        
        Optional<Aluno> alunoOptional = repository.findById(alunoId);
        if (alunoOptional.isPresent()) {
            Aluno aluno = alunoOptional.get();
            mv.addObject("aluno", aluno);
        } else {
            mv.setViewName("erro"); // Assumindo que você tenha uma página de erro
            mv.addObject("mensagem", "Aluno não encontrado");
        }
        
		return mv;
	}
}
