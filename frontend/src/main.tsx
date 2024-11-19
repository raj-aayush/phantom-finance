import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import {BrowserRouter, Routes, Route, Outlet, Navigate} from "react-router-dom";
import './index.css'
import CustomerList from "./containers/CustomerList.tsx";
import CustomerAccountsList from "./containers/CustomerAccountsList.tsx";
import AccountHistory from "./containers/AccountHistory.tsx";
import CustomerCreate from "./containers/CustomerCreate.tsx";
import TransactionCreate from "./containers/TransactionCreate.tsx";
import Login from "./containers/Login.tsx";
import axios from "axios";
import dayjs from 'dayjs';
import relativeTime from 'dayjs/plugin/relativeTime';
import utc from 'dayjs/plugin/utc';

dayjs.extend(relativeTime);
dayjs.extend(utc);

const ProtectedRoute = () => {
    let authToken = localStorage.getItem('token');
    if(!!authToken && !axios.defaults.headers.common['Authorization']) {
        axios.defaults.headers.common['Authorization'] = `Bearer ${authToken}`;
    }
    return authToken ? <Outlet /> : <Navigate to="/login" replace />
}

createRoot(document.getElementById('root')!).render(
  <StrictMode>
      <BrowserRouter>
          <Routes>
              <Route path="/login" element={<Login />} />
              <Route element={<ProtectedRoute />}>
                  <Route path="/*" element={<CustomerList />} />
                  <Route path="/customers/:customerId" element={<CustomerAccountsList />} />
                  <Route path="/customers/:customerId/accounts/:accountId" element={<AccountHistory />} />
                  <Route path="/customers/new" element={<CustomerCreate />} />
                  <Route path="/transactions/new" element={<TransactionCreate />} />
              </Route>
          </Routes>
      </BrowserRouter>
  </StrictMode>,
)
