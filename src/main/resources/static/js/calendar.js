let stompClient = null;

const mySenderId = "user-" + Math.random().toString(36).substring(2, 9);

function main() {
    initEvents();
    let bttn = document.getElementById("announceButton");
    if (bttn) {
        bttn.addEventListener("click", sendAnnouncement);
    }
    
    connect(); 

}

function initEvents() {
    for (let i = 1;; i++) {
        let day_container = document.getElementById(`day-${i}`);
        if (!day_container) {
            break;
        }
        
        day_container.addEventListener("click", showAddEventPopup);
    }

    document.getElementById("bttn-close-popup").addEventListener("click", showAddEventPopup);

    let date = new Date();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();

    getEvents(month, year);
}

function showAddEventPopup(e) {
    let day_num = e.target.id.replace(/^\D+/g, '');
    document.getElementById("create-event-popup").classList.toggle("show");
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
            // stompClient.subscribe("/topic/events", (msg) => {
            //     const data = JSON.parse(msg.body);
            //     console.log("Event received:", data); 
            //     // if (data.senderId && data.senderId !== mySenderId) {
            //         // showAnnouncement(data);
            //     // }
            //
            //     console.log("Event name: " + data.title);
            // });

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

function getEvents(month, year) {
    let url = `http://${location.host}`;
    fetch(`${url}/events/${month}/${year}`)
    .then(response => {
        if (!response.ok) {
            throw new Error("Could not connect to server");
        }
        return response.json();
    })
    .then(data => {
        console.log(data);
    })
    .catch(error => {
        console.error(error);
    });
}

function createEvent() {

}

window.onload = main;
