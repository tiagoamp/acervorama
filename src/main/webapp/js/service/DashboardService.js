class DashboardService {

    getMediaItems() {
        return new Promise( (resolve, reject) => { 
            $.get("http://localhost:8080/acervorama/webapi/media", 
                    function(data) {
                        resolve(data);
                    }
                )
                .fail( function() { 
                    reject("Fail acessing database..."); 
                    } 
                );
            } 
        );
    }

}