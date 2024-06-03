package com.example.demo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyController {
	
	@Autowired
    private EmailService emailService;

	private final pcJDBCTemp pcJDBCTemp;

    @Autowired
    public MyController(pcJDBCTemp pcJDBCTemp) {
        this.pcJDBCTemp = pcJDBCTemp;
    }
    @GetMapping("/")
	 public String getStore(Model model){
		
    	ArrayList <computer> lista = pcJDBCTemp.ritornaPc();
		 model.addAttribute("lista", lista);
		 
		 return "store";
		 
	 }
	 
	 @GetMapping("/getMagazzino")
	 public String getOrologioForm(){
		
		 return "formMagazzino";
		 
	 }

	 
	 @GetMapping("/getPc")
	 public String getAll(Model model){
		
		 ArrayList <computer> lista = pcJDBCTemp.ritornaPc();
		 model.addAttribute("lista", lista);
		 
		 return "listaPc";
		 
	 }
	 
	 
	 @PostMapping("/AddPc")
	 public String addPc(@RequestParam("marca") String marca, @RequestParam("tipologia") String tipologia, @RequestParam("modello") String modello, @RequestParam("descrizione") String descrizione, @RequestParam("qnt") String qnt, @RequestParam("url") String url, @RequestParam("prezzo") String prezzo, Model model) {
		 
		 String [] ImgUrl = url.split("\"");
			
			String urlImage = ImgUrl[3];
		 
		 computer o1 = new computer();
		 o1.setMarca(marca);
		 o1.setTipologia(tipologia);
		 o1.setModello(modello);
		 o1.setDescrizione(descrizione);
		 o1.setQnt(Integer.parseInt(qnt));
		 o1.setUrl(urlImage);
		 o1.setPrezzo(Double.parseDouble(prezzo));
		 System.out.println(o1.url);
		 model.addAttribute("computer", o1);
		 pcJDBCTemp.insertPc(marca, tipologia, modello, descrizione, Integer.parseInt(qnt), urlImage, Double.parseDouble(prezzo));
		 return "AddPc";
		 
	 }
	 @PostMapping("/buyPc")
	 public String addWatch(@RequestParam("ordini") String [] ordini,@RequestParam("quantities") String [] pezzi,Model model){
		 
		 ArrayList <computer> lista = pcJDBCTemp.ritornaPc();
		 ArrayList<Integer> qnt = new ArrayList <>();
		 ArrayList<pcOrd> pc = new ArrayList <>();
		 
		 for (String s: pezzi) {
			 if (!s.isEmpty()) {
			int x = Integer.parseInt(s);
			qnt.add(x);
			
		 }}
		 double prezzo = 0;
		 
		
			 
			 for (int j = 0; j < ordini.length; j++) {
				 for (int i = 0; i < lista.size(); i++) {
				 if (lista.get(i).id == (Integer.parseInt(ordini[j]))) {
					
					 pcJDBCTemp.updatePezzi(qnt.get(j),lista.get(i).id  );
					 prezzo += lista.get(i).getPrezzo() * qnt.get(j);
					 pcOrd computer = new pcOrd();
					 computer.setModello(lista.get(i).getModello());
					 computer.setQnt(qnt.get(j));
					 pc.add(computer);
					 
				 }
				 
			 }
		 }
		
		
		System.out.println(prezzo);
		
   model.addAttribute("lista", pc);
  
   model.addAttribute("prezzo", prezzo);
   
		 
		
		 
		
		 
		 return "conferma";
	 }
	
	 
	 
	 

	@GetMapping("/formEmail")
	public String formEmail(){
		
		return "formEmail";
	}
	
	
    @PostMapping("sendEmail")
    public ResponseEntity<String> sendEmail(@RequestParam("to") String to, @RequestParam("subject") String subject, @RequestParam("text") String text) {
        emailService.sendSimpleEmail(to, subject, text);
        return ResponseEntity.ok("Email sent successfully");
    }
}


