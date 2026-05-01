let stompClient = null;

function main() {
    let bttn = document.getElementById("announceButton");
    if (bttn) {
        bttn.addEventListener("click", sendAnnouncement);
    }
    
    connect(); 
}

function sendAnnouncement() {
    let titleInput = document.getElementById("announcementTitle");
    let contentInput = document.getElementById("announcementContent");

    stompClient.publish({
        destination: "/app/announcement", 
        body: JSON.stringify({
            "title": titleInput.value,
            "content": contentInput.value
        })
    });

    titleInput.value = "";
    contentInput.value = "";
}

function connect() {
    let url = `ws://${location.host}/ws`;
    stompClient = new StompJs.Client({
        brokerURL: url,
        onConnect: () => {
            stompClient.subscribe("/topic/announcements", (msg) => {
                showAnnouncement(JSON.parse(msg.body));
            });
        }
    });
    stompClient.activate();
}

window.onload = main;