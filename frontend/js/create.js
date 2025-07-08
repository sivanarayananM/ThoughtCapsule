document.getElementById('createForm').addEventListener('submit', async (e) => {
  e.preventDefault();

  const responseMsg = document.getElementById('responseMsg');
  responseMsg.textContent = ''; // clear previous messages

  // Prepare payload
  const payload = {
    senderName: document.getElementById('senderName').value.trim(),
    recipientEmail: document.getElementById('recipientEmail').value.trim(),
    content: document.getElementById('content').value.trim(),
    mediaUrl: document.getElementById('mediaUrl').value.trim() || null,
    unlockAt: document.getElementById('unlockAt').value,
    pinCode: parseInt(document.getElementById('pinCode').value, 10),
    readOnce: document.getElementById('readOnce').checked,
  };

  // Basic client validation
  if (!payload.senderName || !payload.recipientEmail || !payload.content || !payload.unlockAt || !payload.pinCode) {
    responseMsg.style.color = 'red';
    responseMsg.textContent = 'Please fill all required fields correctly.';
    return;
  }

  try {
    const res = await fetch('http://localhost:8080/api/messages/create', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payload),
    });

    if (res.ok) {
      const text = await res.text();
      responseMsg.style.color = '#2e7d32'; // green
      responseMsg.textContent = text || 'Capsule created successfully!';
      document.getElementById('createForm').reset();
    } else {
      const errorText = await res.text();
      responseMsg.style.color = 'red';
      responseMsg.textContent = errorText || 'Failed to create capsule.';
    }
  } catch (err) {
    responseMsg.style.color = 'red';
    responseMsg.textContent = 'Error: ' + err.message;
  }
});
