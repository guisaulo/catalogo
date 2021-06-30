package com.guilherme.catalago.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.guilherme.catalago.model.Musica;
import com.guilherme.catalago.service.CatalagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CatalogoController {
    
    @Autowired
    CatalagoService catalogoService;

    @RequestMapping(value="/musicas", method = RequestMethod.GET)
    public ModelAndView getMusicas(){
        ModelAndView mv = new ModelAndView("musicas");
        List<Musica> musicas = catalogoService.findAll();
        mv.addObject("musicas", musicas);
        return mv;
    }

    @RequestMapping(value="/musicas/{id}", method = RequestMethod.GET)
    public ModelAndView getMusicaDetalhes(@PathVariable("id") long id){
        ModelAndView mv = new ModelAndView("musicaDetalhes");
        Musica musica = catalogoService.findById(id);
        mv.addObject("musica", musica);
        return mv;
    }

    @RequestMapping(value="/addMusicaForm", method = RequestMethod.GET)
    public String getAddMusicaForm(){
        return "addMusicaForm";
    }

    @RequestMapping(value="/addMusicaForm", method = RequestMethod.POST)
    public String salvarMusica(@Valid Musica musica, BindingResult result, RedirectAttributes attributes) {
        
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem", "Campos obrigratórios não foram preenchidos!!!");
            return "redirect:/addMusicaForm";
        }
        
        musica.setData(LocalDate.now());
        catalogoService.save(musica);
        return "redirect:/musicas";
    }

    @RequestMapping(value = "/deleteMusica/{id}", method=RequestMethod.GET)
    public String deleteMusica(@PathVariable("id") Long id) {
        catalogoService.deleteById(id);
        return "redirect:/musicas";
    }

    @RequestMapping(value="/editMusicaForm/{id}", method = RequestMethod.GET)
    public ModelAndView getMusicaFormAlterarMusica(@PathVariable("id") Long id) {
        ModelAndView mv;
        
        Musica musica = catalogoService.findById(id);
        if (musica != null) {
            mv = new ModelAndView("editMusicaForm");
            mv.addObject("musica", musica);
        } 
        else 
            mv = new ModelAndView("redirect:/musicas");

        return mv;
    }

    @RequestMapping(value="/editMusicaForm/{id}", method=RequestMethod.POST)
    public String salvarEditarMusica(@PathVariable("id") Long id, @Valid Musica musica, BindingResult result, RedirectAttributes attributes) {
        Musica musicaDB = catalogoService.findById(id);

        if (musicaDB == null) 
            return "redirect:/musicas";

        if (result.hasErrors()) {
            attributes.addFlashAttribute("msg","Campos obrigatórios não informados.");
            return "redirect:/editMusicaForm/" + id;
        }

        musicaDB.setAutor(musica.getAutor());
        musicaDB.setLetra(musica.getLetra());
        musicaDB.setTitulo(musica.getTitulo());
        musicaDB.setData(LocalDate.now());
        catalogoService.save(musicaDB);

        return "redirect:/musicas/" + id;
    }
}