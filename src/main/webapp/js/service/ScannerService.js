class ScannerService {

    scanForMedia(from, mediatype) {
        let input = { type: mediatype, dirPath: from };
        
        return new Promise( (resolve,reject) => {
            $.get("http://localhost:8080/acervorama/webapi/scanner", input, 
                    function( data ) {
                        resolve(data);
                    }
                )
                .fail( function() { 
                    reject("Fail to scan.") 
                    } 
                );            
            } 
        );
    }

    saveMediaFile(filepath, mediatype) {
        let mediaitem = {
            "filePath":filepath,
            "type":mediatype
        };
    
        return new Promise( (resolve, reject) => {
            $.ajax({
                url:"http://localhost:8080/acervorama/webapi/media",
                type:"POST",
                headers: { 
                    "Content-Type":"application/json"
                },
                data: JSON.stringify(mediaitem),
                dataType:"json"
            })
            .done( function(data) { resolve(data) } 
            )
            .fail( function(xhr) { reject("Fail to save file. Error: " + xhr) } 
            );
                
        } );
    }

    findAll() {
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