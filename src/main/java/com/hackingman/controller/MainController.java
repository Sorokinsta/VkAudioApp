package com.hackingman.controller;


import com.hackingman.service.AudioService;
import com.hackingman.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private AudioService audioService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/{vkId}")
    public ResponseEntity<String[]> getAudios(@PathVariable("vkId") String vkId) throws InterruptedException {

        return ResponseEntity.ok(audioService.getAudioByVkId(vkId));
    }

    @GetMapping("/{vkId}/play/{number}")
    public ResponseEntity playFirstAudio(@PathVariable("vkId") String vkId,
                                         @PathVariable("number") int number) throws InterruptedException {
        audioService.startAudio(vkId,number);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stop")
    public ResponseEntity stopAudio() throws InterruptedException {
        audioService.stopMusic();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{vkId}/message")
    public ResponseEntity sendMessage(@PathVariable("vkId") String vkId) throws InterruptedException {
        messageService.sendMessage(vkId);
        return ResponseEntity.ok().build();
    }
}
