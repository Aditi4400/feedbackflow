
import { useEffect, useState } from 'react'
import { checkBackendHealth } from './services/healthService'
import './App.css'

function App() {
  const [status, setStatus] = useState('Checking...')
  const [message, setMessage] = useState('')

  useEffect(() => {
    checkBackendHealth()
      .then((data) => {
        setStatus(data.status)
        setMessage(data.message)
      })
      .catch(() => {
        setStatus('DOWN')
        setMessage('Unable to connect to backend')
      })
  }, [])

  return (
    <div>
      <h1>FeedbackFlow</h1>

      <h2>Backend Connection Status</h2>

      <p>Status: {status}</p>
      <p>{message}</p>
    </div>
  )
}

export default App
