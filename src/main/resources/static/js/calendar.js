let stompClient = null;

const mySenderId = "user-" + Math.random().toString(36).substring(2, 9);

function main() {
    blockForm();
    initEvents();
    let bttn = document.getElementById("announceButton");
    if (bttn) {
        bttn.addEventListener("click", sendAnnouncement);
    }
    
    connect(); 
}

function blockForm() {
    let form = document.getElementById("create-event-form");
    form.addEventListener("submit", onFormSubmit);
}

function onFormSubmit(e) {
    e.preventDefault();

    let title = document.getElementById("title");
    let description = document.getElementById("description");
    let date = document.getElementById("date");
    let time = document.getElementById("time");
    let members = document.getElementById("members");

    if (title.value.trim().length == 0) {
        title.focus();
        return;
    }

    if (date.value.trim().length == 0) {
        date.focus();
        return;
    }

    let event = {
        "id": -1,
        "title": title.value,
        "description": description.value,
        "eventDate": date.value,
        "eventTime": time.value,
        // "memberIds": members.value,
        "memberIds": [],
        "createdAt": "",
        "updatedAt": ""
    };

    createEvent(JSON.stringify(event));
}

function initEvents() {
    for (let i = 1;; i++) {
        let day_container = document.getElementById(`day-${i}`);
        if (!day_container) {
            break;
        }
        
        day_container.addEventListener("click", toggleAddEventPopup);
    }

    document.getElementById("bttn-close-popup").addEventListener("click", toggleAddEventPopup);

    let date = new Date();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();

    getEvents(month, year);
}

function toggleAddEventPopup(e) {
    let currentDate = new Date();

    let year = currentDate.getFullYear();
    let month = currentDate.getMonth();
    let day = e.target.id.replace(/^\D+/g, '');

    let date = new Date(year, month, day);

    document.getElementById("date").value = date.toISOString().substring(0, 10);
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

function createEvent(event) {
    console.log("Sending:");
    console.log(event);

    let url = `http://${location.host}`;
    fetch(`${url}/events/create`, {method: "POST", body: event})
    .then(response => {
        if (!response.ok) {
            throw new Error("Could not connect to server");
        }
    })
    .then(data => {
        console.log(data);
    });
}

window.onload = main;
