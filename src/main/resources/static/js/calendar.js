let stompClient = null;

const mySenderId = "user-" + Math.random().toString(36).substring(2, 9);

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
            "content": contentInput.value,
            "senderId" : mySenderId
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
                const data = JSON.parse(msg.body);
                console.log("Announcement received:", data); 
                if (data.senderId && data.senderId !== mySenderId) {
                    showAnnouncement(data);
                }
            });
        }
    });
    stompClient.activate();
}

window.onload = main;