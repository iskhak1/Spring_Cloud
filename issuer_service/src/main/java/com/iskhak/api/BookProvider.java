package com.iskhak.api;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BookProvider {

    private final WebClient webClient;
    private final EurekaClient eurekaClient;

    public BookProvider(EurekaClient eurekaClient){
        webClient = WebClient.builder().build();
        this.eurekaClient = eurekaClient;
    }

    public UUID getRandomBookId(){

        BookResponse randomBook  = webClient.get()
               .uri(getBookServiceIp()+"/api/book/random")
               .retrieve()
               .bodyToMono(BookResponse.class)
               .block();

        return randomBook.getId();

    }

    private String getBookServiceIp(){
        Application application = eurekaClient.getApplication("BOOK_SERVICE");
        List<InstanceInfo> instances = application.getInstances();
        int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
        InstanceInfo randomInstance = instances.get(randomIndex);
        return "http://" + randomInstance.getIPAddr() + ":" + randomInstance.getPort();
    }

    @Data
    public static class BookResponse{
        private UUID id;
    }



}
