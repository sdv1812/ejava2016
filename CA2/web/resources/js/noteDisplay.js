$(function() {

        displayNotes = function(text) {
   
		$("#noteDisplayArea").val(text + "\n" + $("#noteDisplayArea").val());
	}

        var displayNotes;
         var url = "ws://localhost:8080/ca2/displayNotes";
         var socket = new WebSocket(url);

	$("#Ok").on("click", function() {
               $("#noteDisplayArea").val('');
		socket.send($("#category").val());
	})

	socket.onmessage = function(evt) {
		// {message: "the message" , timestamp: "time" }
		var msg = JSON.parse(evt.data);
                $("#noteDisplayArea").val('');
                msg.forEach(function(d){
                displayNotes("Created on : "+d.timestamp+",  Created By : "+d.user +"\n");
                displayNotes("Content : "+d.content);
                displayNotes("Note Title : "+ d.title+",  Category: "+d.category);
                });
			}
	socket.onopen = function() {
		displayNotes("Connected to chat server");
	}
	socket.onclose = function() {
                 console.log("connection is logging");
		displayNotes("Disconnected from chat server");
	}

});



