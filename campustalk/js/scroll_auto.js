window.onload = function() {
    setTimeout(function() {
        const messageElements = document.querySelectorAll(".message");
        if (messageElements.length > 0) {
            const lastMessage = messageElements[messageElements.length - 1];
            lastMessage.scrollIntoView({ behavior: "smooth", block: "end" });
        }
    }, 500);
};