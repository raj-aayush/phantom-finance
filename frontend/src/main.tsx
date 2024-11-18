import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import './index.css'
import CustomerList from "./containers/CustomerList.tsx";
import CustomerAccountsList from "./containers/CustomerAccountsList.tsx";
import AccountHistory from "./containers/AccountHistory.tsx";
import CustomerCreate from "./containers/CustomerCreate.tsx";
import TransactionCreate from "./containers/TransactionCreate.tsx";
import AccountsList from "./containers/AccountsList.tsx";

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <BrowserRouter>
          <Routes>
              <Route path="/customers" element={<CustomerList />} />
              <Route path="/customers/:customerId" element={<CustomerAccountsList />} />
              <Route path="/customers/new" element={<CustomerCreate />} />
              <Route path="/accounts" element={<AccountsList />} />
              <Route path="/accounts/:accountId" element={<AccountHistory />} />
              <Route path="/transactions/new" element={<TransactionCreate />} />
          </Routes>
      </BrowserRouter>
  </StrictMode>,
)
