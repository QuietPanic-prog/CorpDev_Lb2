package ru.kafpin.lb2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ru.kafpin.lb2.model.Serviceman;
import ru.kafpin.lb2.repository.ServicemanRepository;

@Controller
@RequestMapping("/servicemen")
public class ServicemanController {

    private final ServicemanRepository servicemanRepository;

    public ServicemanController(ServicemanRepository servicemanRepository) {
        this.servicemanRepository = servicemanRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("servicemen", servicemanRepository.findAll());
        return "servicemen/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return servicemanRepository.findById(id)
                .map(serviceman -> {
                    model.addAttribute("serviceman", serviceman);
                    return "servicemen/details";
                })
                .orElseGet(() -> redirectWithError(redirectAttributes, "Военнослужащий не найден."));
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("serviceman", new Serviceman());
        model.addAttribute("pageTitle", "Добавление военнослужащего");
        return "servicemen/form";
    }

    @PostMapping
    public String create(@ModelAttribute Serviceman serviceman, RedirectAttributes redirectAttributes) {
        serviceman.setId(null);
        servicemanRepository.save(serviceman);
        redirectAttributes.addFlashAttribute("successMessage", "Запись успешно добавлена.");
        return "redirect:/servicemen";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return servicemanRepository.findById(id)
                .map(serviceman -> {
                    model.addAttribute("serviceman", serviceman);
                    model.addAttribute("pageTitle", "Редактирование военнослужащего");
                    return "servicemen/form";
                })
                .orElseGet(() -> redirectWithError(redirectAttributes, "Невозможно изменить запись: военнослужащий не найден."));
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Serviceman form,
            RedirectAttributes redirectAttributes) {
        var existing = servicemanRepository.findById(id);
        if (existing.isEmpty()) {
            return redirectWithError(redirectAttributes, "Невозможно изменить запись: военнослужащий не найден.");
        }

        Serviceman serviceman = existing.get();
        serviceman.setLastName(form.getLastName());
        serviceman.setFirstName(form.getFirstName());
        serviceman.setPatronymic(form.getPatronymic());
        serviceman.setNationality(form.getNationality());
        serviceman.setBirthDate(form.getBirthDate());
        serviceman.setPosition(form.getPosition());
        serviceman.setRank(form.getRank());
        servicemanRepository.save(serviceman);

        redirectAttributes.addFlashAttribute("successMessage", "Изменения сохранены.");
        return "redirect:/servicemen/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!servicemanRepository.existsById(id)) {
            return redirectWithError(redirectAttributes, "Невозможно удалить запись: военнослужащий не найден.");
        }

        servicemanRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Запись удалена.");
        return "redirect:/servicemen";
    }

    private String redirectWithError(RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute("errorMessage", message);
        return "redirect:/servicemen";
    }
}
