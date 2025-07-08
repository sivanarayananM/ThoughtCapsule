document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("unlockForm");
  const display = document.getElementById("messageDisplay");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const messageId = document.getElementById("messageId").value.trim();
    const pinCode = document.getElementById("pinCode").value.trim();

    if (!messageId || !pinCode) {
      display.innerText = "❌ Message ID and PIN are required.";
      display.style.color = "red";
      return;
    }

    try {
      const res = await fetch(`http://localhost:8080/api/messages/unlock/${messageId}/${pinCode}`);
      const data = await res.json();

      if (res.ok) {
        let result = `📬 <strong>Message:</strong><br>${data.message}`;
        if (data.mediaUrl) {
          result += `<br><br>🔗 <strong>Media:</strong> <a href="${data.mediaUrl}" target="_blank" rel="noopener noreferrer">${data.mediaUrl}</a>`;
        }
        display.innerHTML = result;
        display.style.color = "#222";
      } else {
        display.innerText = data.message || "❌ Something went wrong.";
        display.style.color = "red";
      }
    } catch (error) {
      display.innerText = "❌ Network error. Please try again later.";
      display.style.color = "red";
    }
  });
});
