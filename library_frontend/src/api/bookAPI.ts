import axios from 'axios'

const API_URL = 'http://localhost:8080/api/books'
const CATEGORY_URL = 'http://localhost:8080/api/categories'

export const getCategories = async () => {
  const res = await axios.get(CATEGORY_URL)
  return res.data.data 
}

export const createCategory = async (data: { name: string }) => {
  const res = await axios.post(CATEGORY_URL, data)
  return res.data
}

export const createBook = async (data: any) => {
  const res = await axios.post(API_URL, data)
  return res.data
}
