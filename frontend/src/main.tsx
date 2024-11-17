import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import './index.css'
import CustomerList from "./containers/CustomerList.tsx";
import CustomerAccountsList from "./containers/CustomerAccountsList.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <BrowserRouter>
          <Routes>
              <Route path="*" element={<CustomerList />} />
              <Route path="/customers/:customerId" element={<CustomerAccountsList />} />
          </Routes>
      </BrowserRouter>
  </StrictMode>,
)
