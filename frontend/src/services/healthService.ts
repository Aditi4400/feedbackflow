
import axios from 'axios'

interface HealthResponse {
  status: string
  message: string
}

export const checkBackendHealth = async (): Promise<HealthResponse> => {
  const response = await axios.get<HealthResponse>('/api/health')
  return response.data
}
