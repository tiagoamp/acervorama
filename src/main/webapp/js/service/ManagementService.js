class ManagementService {

    searchByParams(filepathParam, filenameParam, classificationParam, tagsParam, typeParam) {
        let input = { type: typeParam, filepath: filepathParam, filename: filenameParam, classification: classificationParam, tags: tagsParam };
        return new Promise( (resolve,reject) => {
            $.get("http://localhost:8080/acervorama/webapi/media", input, 
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

    getMediaItem(itemId) {
        return new Promise( (resolve,reject) => {
            $.get("http://localhost:8080/acervorama/webapi/media/" + itemId, 
                    function( data ) {
                        resolve(data);
                    }
                )
                .fail( function() { 
                    reject("Fail to connect to database...") 
                    } 
                );            
            } 
        );      
    }

    updateMediaItem(editedItem) {
        return new Promise( (resolve,reject) => {
            $.ajax({
                url:"http://localhost:8080/acervorama/webapi/media/" + editedItem.id,
                type:"PUT",
                headers: { 
                  "Content-Type":"application/json"
                },
                data: JSON.stringify(editedItem),
                dataType:"json",
            })
            .done( function(data) { resolve(data) } 
            )
            .fail( function(xhr) { reject("Fail to save file. Error: " + xhr) } 
            );            
            } 
        );
    }

    deleteMediaItem(itemId) {
        return new Promise( (resolve,reject) => {
            $.ajax({
                url:"http://localhost:8080/acervorama/webapi/media/" + itemId,
                type:"DELETE"                
            })
            .done( function(data) { resolve(data) } 
            )
            .fail( function(xhr) { reject("Fail to delete file. Error: " + xhr) } 
            );            
            } 
        );
    }

}