import axios from 'axios'

const CATEGORY_URL = 'http://localhost:8080/api/categories'

export const getCategories = async () => {
  const res = await axios.get(CATEGORY_URL)
  return res.data.data
}

export const createCategory = async (categoryData) => {
  const token = sessionStorage.getItem('token')
  return axios.post('http://localhost:8080/api/categories', categoryData, {
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  })
}

export const createBook = async (bookData) => {
  const token = sessionStorage.getItem('token')
  return axios.post('http://localhost:8080/api/books', bookData, {
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    },
  })
}
